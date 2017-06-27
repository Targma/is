/**
 *
 * App.react.js
 *
 * This component is the skeleton around the actual pages, and should only
 * contain code that should be seen on all pages. (e.g. navigation bar)
 *
 * NOTE: while this component should technically be a stateless functional
 * component (SFC), hot reloading does not currently support SFCs. If hot
 * reloading is not a necessity for you then you can refactor it and remove
 * the linting exception.
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import styled from 'styled-components';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';
import Header from '../../components/Header/index';
import Footer from '../../components/Footer/index';
import { login, storeAddresses } from '../App/actions';
import { init } from '../../utils/keycloak';
import { getCustomerLogin, getAddresses } from '../../utils/apiResources';

const AppWrapper = styled.div`
  max-width: 60%;
  margin: 0 auto;
  display: flex;
  min-height: 100%;
  padding: 0 16px;
  flex-direction: column;
`;

class App extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  componentDidMount() {
    init(() => {
      getCustomerLogin().then((customer) => {
        this.props.login(customer);
      });
      getAddresses().then((addresses) => {
        this.props.setAddresses(addresses);
      });
    });
  }

  render() {
    return (
      <AppWrapper>
        <Header />
        <div>
          {React.Children.toArray(this.props.children)}
        </div>
        <Footer />
        <ToastContainer hideProgressBar position={toast.POSITION.BOTTOM_RIGHT} />
      </AppWrapper>
    );
  }
}

App.propTypes = {
  children: React.PropTypes.node,
  login: PropTypes.func,
  setAddresses: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
});

function mapDispatchToProps(dispatch) {
  return {
    login: (customer) => dispatch(login(customer)),
    setAddresses: (addresses) => dispatch(storeAddresses(addresses)),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
