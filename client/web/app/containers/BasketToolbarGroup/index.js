/*
 *
 * BasketToolbarGroup
 *
 */

import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { RaisedButton } from 'material-ui';
import { ToolbarGroup } from 'material-ui/Toolbar';
import FontAwesome from 'react-fontawesome';
import { toggleOpen } from '../App/actions';

export class BasketToolbarGroup extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <ToolbarGroup>
        <RaisedButton
          label={<span> <FontAwesome name={'shopping-basket'} /> Basket </span>}
          onClick={this.props.toggleBasketOpen}
          style={{ color: 'white', float: 'left' }}
        />
      </ToolbarGroup>
    );
  }
}

BasketToolbarGroup.propTypes = {
  toggleBasketOpen: PropTypes.func,
};


function mapDispatchToProps(dispatch) {
  return {
    toggleBasketOpen: () => dispatch(toggleOpen()),
  };
}

export default connect(null, mapDispatchToProps)(BasketToolbarGroup);
