import Keycloak from 'keycloak-js';
import keycloakConfig from '../keycloak.json';
import { setToken } from './apiResources';

let keycloak = null;

function init(successCallback) {
  keycloak = Keycloak(keycloakConfig);
  keycloak.init({ onLoad: 'check-sso' }).success((authenticated) => {
    if (authenticated) {
      initRefreshTimer();
      setApiToken();
      successCallback();
    }
  }).error((event) => {
    console.log(event);
  });
}

function initRefreshTimer() {
  setInterval(() => {
    keycloak.updateToken(10).success(() => {
      setApiToken();
    }).error(() => {
      keycloak.logout();
    });
  }, 10000);
}

function setApiToken() {
  setToken(keycloak.token);
}

function keycloakLogin() {
  keycloak.login();
}

function keycloakLogout() {
  keycloak.logout();
}

export {
  init,
  keycloakLogin,
  keycloakLogout,
};
