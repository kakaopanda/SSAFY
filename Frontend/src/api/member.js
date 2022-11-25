import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token");
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function deleteUser(userid, success, fail) {
  await api.get(`/user/delete/${userid}`).then(success).catch(fail);
}

async function modifyUser(user, success, fail) {
  await api.post(`/user/modify`, user).then(success).catch(fail);
}

async function confirmUserPwd(current, success, fail) {
  await api.post(`/user/confirm`, current).then(success).catch(fail);
}

export { login, logout, findById, tokenRegeneration, deleteUser, modifyUser, confirmUserPwd};
