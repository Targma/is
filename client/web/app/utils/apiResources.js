import 'whatwg-fetch';

export const HOST_URL = 'http://localhost:8080';
export const API_PREFIX = 'is/api';
export const API_VERSION = 'v1';

export const API_PATH = `${HOST_URL}/${API_PREFIX}/${API_VERSION}`;

export const RESOURCE_PRODUCT = `${API_PATH}/Product`;
export const RESOURCE_ORDER = `${API_PATH}/Order`;
export const RESOURCE_USER = `${API_PATH}/User`;
export const RESOURCE_ADDRESS = `${API_PATH}/Address`;

let authorizationToken = '';

function buildHeaders() {
  const isHeaders = new Headers();
  isHeaders.append('Content-Type', 'application/json');
  isHeaders.append('Accept', 'application/json');
  isHeaders.append('Authorization', `Bearer ${authorizationToken}`);

  return isHeaders;
}

function parseJSON(response) {
  return response.json();
}

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

function setToken(token) {
  authorizationToken = token;
}

function getCustomerLogin() {
  const isConfig = {
    method: 'GET',
    headers: buildHeaders(),
    mode: 'cors',
    cache: 'default' };

  return fetch(`${API_PATH}/Customer/login`, isConfig)
     .then(checkStatus)
     .then(parseJSON);
}

function getProducts(search) {
  console.log(`Called getProduct with search: ${search}`);

  const params = {
    where: `isLatest:eq:true|isDeleted:eq:false|title:likeic:%${search}%`,
    limit: '10',
    skip: '0',
  };

  const esc = encodeURIComponent;
  const query = Object.keys(params)
    .map((k) => `${esc(k)}=${esc(params[k])}`)
    .join('&');

  const isConfig = {
    method: 'GET',
    headers: buildHeaders(),
    mode: 'cors',
    cache: 'default' };

  return fetch(`${RESOURCE_PRODUCT}?${query}`, isConfig)
    .then(checkStatus)
    .then(parseJSON);
}

export {
  getCustomerLogin,
  getProducts,
  setToken,
};