document.addEventListener("DOMContentLoaded", () => {
  const loginBtn = document.querySelector('#btn-login');
  if (loginBtn) {
    loginBtn.addEventListener('click', () => {
      login();
    });
  } else {
    console.error("로그인 버튼을 찾을 수 없습니다.");
  }
});

/**
 * 로그인 요청
 * @returns {Promise<void>}
 */
function login() {
  const role = document.querySelector(
      "input[type=radio][name=role]:checked").value;
  const email = document.querySelector("#email").value;
  const password = document.querySelector("#password").value;

  const data = {
    role: role, email: email, password: password
  };

  apiRequest("/api/v1/auth/login", "POST", data)
  .then(token => {
    console.log(`발급된 JWT token: ${token}`);
    if (token) {
      localStorage.setItem(JWT_HEADER, token);
      console.log(`로그인 성공:: local storage: ${localStorage.getItem(JWT_HEADER)}`);
    }
  }).catch(error => {
    alert("로그인에 실패했습니다.");
    console.error("로그인 실패: " + err.message);
  });
}