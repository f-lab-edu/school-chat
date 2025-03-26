document.addEventListener("DOMContentLoaded", () => {
  const loginBtn = document.querySelector('#btn-login');
  if (loginBtn) {
    loginBtn.addEventListener('click', handleLogin);
  } else {
    console.error("로그인 버튼을 찾을 수 없습니다.");
  }
});

/**
 * 로그인 버튼 클릭 핸들러
 */
function handleLogin() {
  login();
}

/**
 * 로그인 요청
 * @returns {Promise<void>}
 */
async function login() {
  const role = document.querySelector("input[type=radio][name=role]:checked").value;
  const email = document.querySelector("#email").value;
  const password = document.querySelector("#password").value;

  try {
    const data ={
      role: role,
      email: email,
      password: password
    };

    const token = await apiRequest("/api/v1/auth/login", "POST", data);
    console.log("Login successful: ", token);
    // if (token) {
    //   localStorage.setItem("jwtToken", token);
    // }
  } catch (err) {
    alert("로그인에 실패했습니다.");
    console.error("로그인 실패: " + err.message);
  }
}