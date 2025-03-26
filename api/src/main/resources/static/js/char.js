
document.addEventListener("DOMContentLoaded", () => {
  const logoutBtn = document.querySelector('#btn-logout');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', handleLogout);
  } else {
    console.error("로그아웃 버튼을 찾을 수 없습니다.");
  }
});
/**
 * 로그아웃 버튼 클릭 핸들러
 */
function handleLogout() {
  logout();
}

/**
 * 로그아웃 요청
 * @returns {Promise<void>}
 */
async function logout() {

  try {
    const token = await apiRequest("/api/v1/auth/logout", "POST");
    console.log("Logout successful: ");
  } catch (err) {
    alert("로그아웃에 실패했습니다.");
    console.error("로그아웃 실패: " + err.message);
  }
}