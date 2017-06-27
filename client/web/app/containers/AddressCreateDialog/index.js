/*
 *
 * AddressCreateDialog
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { Dialog, FlatButton, TextField } from 'material-ui';
import { toast } from 'react-toastify';
import { changedAddressCity, changedAddressCode, changedAddressStreet, addressDialogCancel, addressDialogSubmit } from '../HomePage/actions';
import { makeSelectIsCreateDialogOpen, makeSelectCreateAddress } from '../HomePage/selectors';

export class AddressCreateDialog extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChangeCity = this.handleChangeCity.bind(this);
    this.handleChangeCode = this.handleChangeCode.bind(this);
    this.handleChangeStreet = this.handleChangeStreet.bind(this);

    this.state = {
      codeError: '',
      cityError: '',
      streetError: '',
    };
  }

  handleSubmit() {
    const code = this.props.address.get('code');
    const city = this.props.address.get('city');
    const street = this.props.address.get('street');

    if (code && city && street) {
      this.props.onSubmit();
    } else {
      this.setState((prevState) => (Object.assign({}, prevState, {
        codeError: this.checkValue(code),
        cityError: this.checkValue(city),
        streetError: this.checkValue(street),
      })));
      toast('Invalid from.', { type: toast.TYPE.ERROR });
    }
  }

  checkValue(value) {
    if (value === null || value === '') {
      return 'This field is required';
    }
    return '';
  }

  handleChangeCity(evt) {
    this.state.cityError = this.checkValue(evt.target.value);
    this.props.onCityChange(evt);
  }

  handleChangeCode(evt) {
    this.state.codeError = this.checkValue(evt.target.value);
    this.props.onCodeChange(evt);
  }

  handleChangeStreet(evt) {
    this.state.streetError = this.checkValue(evt.target.value);
    this.props.onStreetChange(evt);
  }

  render() {
    const actions = [
      <FlatButton
        label="Cancel"
        primary
        onTouchTap={this.props.handleClose}
      />,
      <FlatButton
        label="Submit"
        primary
        keyboardFocused
        onTouchTap={this.handleSubmit}
      />,
    ];

    const code = this.props.address.get('code');
    const city = this.props.address.get('city');
    const street = this.props.address.get('street');
    return (
      <Dialog
        title="Dialog With Actions"
        actions={actions}
        modal={false}
        open={this.props.isOpen}
        onRequestClose={this.props.handleClose}
      >
        <TextField errorText={this.state.codeError} hintText="Code" type="text" value={code} onChange={this.handleChangeCode} />
        <TextField errorText={this.state.cityError} hintText="City" type="text" value={city} onChange={this.handleChangeCity} />
        <TextField errorText={this.state.streetError} hintText="Street" type="text" value={street} onChange={this.handleChangeStreet} />
      </Dialog>
    );
  }
}

AddressCreateDialog.propTypes = {
  isOpen: PropTypes.bool,
  onCityChange: PropTypes.func,
  onCodeChange: PropTypes.func,
  onStreetChange: PropTypes.func,
  handleClose: PropTypes.func,
  onSubmit: PropTypes.func,
  address: PropTypes.object,
};

const mapStateToProps = createStructuredSelector({
  isOpen: makeSelectIsCreateDialogOpen(),
  address: makeSelectCreateAddress(),
});

function mapDispatchToProps(dispatch) {
  return {
    onCityChange: (evt) => dispatch(changedAddressCity(evt.target.value)),
    onCodeChange: (evt) => dispatch(changedAddressCode(evt.target.value)),
    onStreetChange: (evt) => dispatch(changedAddressStreet(evt.target.value)),
    handleClose: () => dispatch(addressDialogCancel()),
    onSubmit: () => dispatch(addressDialogSubmit()),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(AddressCreateDialog);
