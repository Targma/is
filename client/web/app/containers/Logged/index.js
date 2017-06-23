import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { IconMenu, MenuItem, IconButton } from 'material-ui';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
import messages from './messages';
import { makeSelectUser } from '../App/selectors';
import { keycloakLogout } from '../../utils/keycloak';

export class Logged extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    const name = this.props.user.name;
    return (
      <div>
        <FormattedMessage {...messages.hello} values={{ name }} />
        <IconMenu
          iconButtonElement={<IconButton><MoreVertIcon /></IconButton>}
          targetOrigin={{ horizontal: 'right', vertical: 'top' }}
          anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
          iconStyle={{ fill: 'rgba(255, 255, 255, 1)' }}
        >
          <MenuItem primaryText="Refresh" />
          <MenuItem primaryText="Help" />
          <MenuItem primaryText="Sign out" onTouchTap={keycloakLogout} />
        </IconMenu>
      </div>
    );
  }
}

Logged.propTypes = {
  user: PropTypes.object,
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  user: makeSelectUser(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Logged);
