
import { fromJS } from 'immutable';
import basketReducer from '../reducer';

describe('basketReducer', () => {
  it('returns the initial state', () => {
    expect(basketReducer(undefined, {})).toEqual(fromJS({}));
  });
});
