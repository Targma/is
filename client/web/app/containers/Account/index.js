import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { makeSelectLogged } from '../App/selectors';

import Login from '../../containers/Login';
import Logged from '../../containers/Logged';

export class Account extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  render() {
    return (
      <div>
        {this.props.logged ? <Logged /> : <Login />}
      </div>
    );
  }
}

Account.propTypes = {
  logged: PropTypes.bool,
};

const mapStateToProps = createStructuredSelector({
  logged: makeSelectLogged(),
});

function mapDispatchToProps(dispatch) {
  return {
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Account);
