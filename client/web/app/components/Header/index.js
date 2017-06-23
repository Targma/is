/**
*
* Header
*
*/

import React from 'react';
import { AppBar } from 'material-ui';
import Account from '../../containers/Account';

class Header extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <AppBar
          title="IS demo"
          iconElementRight={<Account />}
        />
      </div>
    );
  }
}

Header.propTypes = {

};

export default Header;
