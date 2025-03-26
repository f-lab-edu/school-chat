/**
 * API 요청
 * @param url 요청 URL
 * @param method 메서드
 * @param data 데이터
 * @returns {Promise<string>}
 */
async function apiRequest(url, method = "GET", data = null) {
  const headers = {
    "Content-Type": "application/x-www-form-urlencoded"
  };

  // todo JWT 토큰이 있다면 Authorization 헤더에 추가

  const options = {
    method: method, headers: headers,
  };

  if (data) {
    if (method === "GET") {
      const queryString = new URLSearchParams(data).toString();
      url += `?${queryString}`;
    } else if (method === "DELETE") {
      options.body = new URLSearchParams(data).toString();
    } else if (["POST", "PUT", "PATCH"].includes(method)) {
      headers["Content-Type"] = "application/json";
      headers["Accept"] = "application/json";
      options.body = JSON.stringify(data);
    }
  }

  try {
    const response = await fetch(url, options);
    const result = await response.text();
    if (response.ok) {
      return result;
    } else {
      throw new Error(result);
    }
  } catch (err) {
    console.error("API 요청 실패: ", err.message);
    throw err;
  }
}