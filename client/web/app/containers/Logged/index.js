import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { IconMenu, MenuItem, IconButton } from 'material-ui';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
import { toast } from 'react-toastify';
import messages from './messages';
import { makeSelectUser } from '../App/selectors';
import { toggleAddressDialog } from '../HomePage/actions';
import { keycloakLogout } from '../../utils/keycloak';
import AddressCreateDialog from '../AddressCreateDialog';


export class Logged extends React.Component { // eslint-disable-line react/prefer-stateless-function

  componentDidMount() {
    toast(`Hi ${this.props.user.name}`);
  }

  render() {
    const email = this.props.user.email;
    return (
      <div>
        <IconMenu
          iconButtonElement={<IconButton><MoreVertIcon /></IconButton>}
          targetOrigin={{ horizontal: 'right', vertical: 'top' }}
          anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
          iconStyle={{ fill: 'rgba(255, 255, 255, 1)' }}
        >
          <MenuItem disabled primaryText={<FormattedMessage {...messages.loggedAs} values={{ email }} />} />
          <MenuItem primaryText="Add address" onTouchTap={this.props.toggleDialogOpen} />
          <MenuItem primaryText="Sign out" onTouchTap={keycloakLogout} />
        </IconMenu>
        <AddressCreateDialog />
      </div>
    );
  }
}

Logged.propTypes = {
  user: PropTypes.object,
  toggleDialogOpen: PropTypes.func,

};

const mapStateToProps = createStructuredSelector({
  user: makeSelectUser(),
});

function mapDispatchToProps(dispatch) {
  return {
    toggleDialogOpen: () => dispatch(toggleAddressDialog()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(Logged);
