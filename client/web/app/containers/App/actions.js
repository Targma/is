import {
  LOG_IN,
} from './constants';

export function login(user) {
  return {
    type: LOG_IN,
    user,
  };
}
