
import {
  defaultAction,
} from '../actions';
import {
  DEFAULT_ACTION,
} from '../constants';

describe('ProductList actions', () => {
  describe('Default Action', () => {
    it('has a type of ADD_PRODUCT_IN_BASKET', () => {
      const expected = {
        type: DEFAULT_ACTION,
      };
      expect(defaultAction()).toEqual(expected);
    });
  });
});
