import { take, call, put, select, cancel, takeLatest } from 'redux-saga/effects';
import { LOCATION_CHANGE } from 'react-router-redux';
import { debounceFor } from 'redux-saga-debounce-effect';
import { toast } from 'react-toastify';
import { FETCH_PRODUCT, ADDRESS_DIALOG_SUBMIT } from './constants';
import { PLACE_ORDER } from '../App/constants';
import { getProducts, createAddresses, createOrder, LIMIT } from '../../utils/apiResources';
import { makeSelectSelectedAddress, makeSelectAddresses, makeSelectProductInBasket, makeSelectUser } from '../../containers/App/selectors';
import { makeSelectCreateAddress } from './selectors';
import { setProductsError, setProducts, addressDialogSubmitSucessful } from './actions';
import { addAddress, clearBasket } from '../App/actions';

export function* fetchProducts(action) {
  const skip = Number(action.pageNumber) * LIMIT;
  try {
    const repos = yield call(getProducts, action.search, skip);
    const count = repos.headers.get('x-count');
    yield put(setProducts(repos.data, count));
  } catch (err) {
    toast('Error fetching products', { type: toast.TYPE.ERROR });
    yield put(setProductsError(err));
  }
}

export function* createAddress() {
  const addressSelector = yield select(makeSelectCreateAddress());
  const address = {
    city: addressSelector.get('city'),
    code: addressSelector.get('code'),
    street: addressSelector.get('street'),
  };

  try {
    const res = yield call(createAddresses, address);
    yield put(addAddress(res.data));
    yield put(addressDialogSubmitSucessful());
    toast('Address created', { type: toast.TYPE.SUCCESS });
  } catch (err) {
    toast('Error creating address', { type: toast.TYPE.ERROR });
  }
}

export function* placeOrder() {
  const addressIndex = yield select(makeSelectSelectedAddress());
  const addresses = yield select(makeSelectAddresses());

  const address = addresses[addressIndex];
  const customer = yield select(makeSelectUser());
  const productOnOrders = yield select(makeSelectProductInBasket());
  const order = {
    customer: {
      id: customer.id,
    },
    address: {
      id: address.id,
    },
    productOnOrders: productOnOrders.map((item, index) => ({
      product: {
        id: item.product.id,
      },
      quantity: item.quantity,
      orderNumber: index,
    })),
  };
  try {
    const res = yield call(createOrder, order);
    yield put(clearBasket());
    toast('Order created', { type: toast.TYPE.SUCCESS });
  } catch (err) {
    toast('Error creating address', { type: toast.TYPE.ERROR });
  }
}

/**
 * Root saga manages watcher lifecycle
 */
export function* sagasCreateAddress() {
  const watcher = yield takeLatest(ADDRESS_DIALOG_SUBMIT, createAddress);
  yield take(LOCATION_CHANGE);
  yield cancel(watcher);
}

export function* sagasFetchProduct() {
  const watcher = yield debounceFor(FETCH_PRODUCT, fetchProducts, 1000);
  yield take(LOCATION_CHANGE);
  yield cancel(watcher);
}

export function* sagasCreateOrder() {
  const watcher = yield takeLatest(PLACE_ORDER, placeOrder);
  yield take(LOCATION_CHANGE);
  yield cancel(watcher);
}

// Bootstrap sagas
export default [
  sagasFetchProduct,
  sagasCreateAddress,
  sagasCreateOrder,
];
