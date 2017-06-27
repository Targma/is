/**
*
* Header
*
*/

import React from 'react';
import { AppBar, Toolbar } from 'material-ui';
import Account from '../../containers/Account';
import BasketToolbarGroup from '../../containers/BasketToolbarGroup';

class Header extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <AppBar
          title="IS demo"
          iconElementRight={<Account />}
        />
        <Toolbar>
          <BasketToolbarGroup />
        </Toolbar>
      </div>
    );
  }
}

Header.propTypes = {

};

export default Header;
