/*
 *
 * Basket
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import Helmet from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import makeSelectBasket from './selectors';
import messages from './messages';

export class Basket extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <Helmet
          title="Basket"
          meta={[
            { name: 'description', content: 'Description of Basket' },
          ]}
        />
        <FormattedMessage {...messages.header} />
      </div>
    );
  }
}

Basket.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  Basket: makeSelectBasket(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Basket);
