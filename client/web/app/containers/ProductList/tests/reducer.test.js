
import { fromJS } from 'immutable';
import productListReducer from '../reducer';

describe('productListReducer', () => {
  it('returns the initial state', () => {
    expect(productListReducer(undefined, {})).toEqual(fromJS({}));
  });
});
