/*
 * AppConstants
 * Each action has a corresponding type, which the reducer knows and picks up on.
 * To avoid weird typos between the reducer and the actions, we save them as
 * constants here. We prefix them with 'yourproject/YourComponent' so we avoid
 * reducers accidentally picking up actions they shouldn't.
 *
 * Follow this format:
 * export const YOUR_ACTION_CONSTANT = 'yourproject/YourContainer/YOUR_ACTION_CONSTANT';
 */
export const DEFAULT_LOCALE = 'en';

export const LOG_IN = 'web/App/LOG_IN';

export const ADD_PRODUCT_IN_BASKET = 'web/App/ADD_PRODUCT_IN_BASKET';
export const CLEAR_BASKET = 'web/App/CLEAR_BASKET';

export const REMOVE_PRODUCT_IN_BASKET = 'web/App/REMOVE_PRODUCT_IN_BASKET';
export const UPDATE_PRODUCT_QUANTITY_IN_BASKET = 'web/App/UPDATE_PRODUCT_QUANTITY_IN_BASKET';
export const TOGGLE_OPEN_BASKET = 'web/App/TOGGLE_OPEN_BASKET';

export const STORE_ADDRESSES = 'web/App/STORE_ADDRESSES';
export const ADD_ADDRESSES = 'web/App/ADD_ADDRESSES';
export const SELECT_ADDRESSES = 'web/App/SELECT_ADDRESSES';

export const PLACE_ORDER = 'app/ProductList/PLACE_ORDER';
