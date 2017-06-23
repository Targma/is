/*
 *
 * AppHeader reducer
 *
 */
import { fromJS } from 'immutable';
import {
  LOG_IN,
} from './constants';

const initialState = fromJS({
  logged: false,
  user: {},
});

function appReducer(state = initialState, action) {
  switch (action.type) {
    case LOG_IN:
      return state
        .set('user', action.user)
        .set('logged', true);
    default:
      return state;
  }
}

export default appReducer;
