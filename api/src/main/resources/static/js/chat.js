document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.querySelector('#btn-logout');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', ()=>{
      logout();
    });
  } else {
    console.error("로그아웃 버튼을 찾을 수 없습니다.");
  }
});

/**
 * 로그아웃 요청
 * @returns {Promise<void>}
 */
async function logout() {

  await apiRequest("/api/v1/auth/logout", "POST").then(() => {
    localStorage.removeItem(JWT_HEADER);
    console.log(`로그아웃 성공:: local storage: ${localStorage.getItem(JWT_HEADER)}`);
  }).catch(err => {
    alert("로그아웃에 실패했습니다.");
    console.error("로그아웃 실패: " + err.message);
  })

}