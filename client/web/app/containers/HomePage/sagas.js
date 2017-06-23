import { take, call, put, select, cancel, takeLatest } from 'redux-saga/effects';
import { LOCATION_CHANGE } from 'react-router-redux';
import { debounceFor } from 'redux-saga-debounce-effect';
import { SEARCH_CHANGED } from './constants';
import { getProducts } from '../../utils/apiResources';
import { makeSelectSearchTitle } from './selectors';
import { setProductsError, setProducts } from './actions';

export function* fetchProducts() {
  const search = yield select(makeSelectSearchTitle());
  try {
    // Call our request helper (see 'utils/request')
    const repos = yield call(getProducts, search);
    yield put(setProducts(repos));
  } catch (err) {
    yield put(setProductsError(err));
  }
}

/**
 * Root saga manages watcher lifecycle
 */
export function* apiProducts() {
  // Watches for LOAD_REPOS actions and calls getRepos when one comes in.
  // By using `takeLatest` only the result of the latest API call is applied.
  // It returns task descriptor (just like fork) so we can continue execution
  const watcher = yield debounceFor(SEARCH_CHANGED, fetchProducts, 1000);

  // Suspend execution until location changes
  yield take(LOCATION_CHANGE);
  yield cancel(watcher);
}

// Bootstrap sagas
export default [
  apiProducts,
];
