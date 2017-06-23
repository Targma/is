import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { makeSelectLogged } from '../App/selectors';
import { login } from '../App/actions';

import InfoFormat from './InfoFormat';
import Login from '../../containers/Login';
import Logged from '../../containers/Logged';
import { init } from '../../utils/keycloak';
import { getCustomerLogin } from '../../utils/apiResources';

export class Account extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  componentDidMount() {
    init(() => {
      getCustomerLogin().then((customer) => {
        this.props.login(customer);
      });
    });
  }

  render() {
    return (
      <div>
        <InfoFormat>
          {this.props.logged ? <Logged /> : <Login />}
        </InfoFormat>
      </div>
    );
  }
}

Account.propTypes = {
  logged: PropTypes.bool,
  login: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  logged: makeSelectLogged(),
});

function mapDispatchToProps(dispatch) {
  return {
    login: (customer) => dispatch(login(customer)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Account);
