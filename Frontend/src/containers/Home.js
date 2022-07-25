import React from 'react';
import { connect } from 'react-redux';
import Map from '../components/Map/Map'
import { AppointmentList } from '../components';
import AppointmentBox from '../components/AppointmentBox';
import { memoListRequest} from '../actions/memo'

// isLoggedIn={this.props.status.isLoggedIn}
// userId={this.props.status.username}

class Home extends React.Component {
  componentDidMount(){
    this.props.memoListRequest(true).then(
      ()=>{
        console.log("List mounted: ", this.props.memoData);
      }
    );
  }

  render() {
    return (
      <div className='wrapper'>
        <Map />
        <AppointmentList data={this.props.memoData} currentUser={this.props.currentUser}/>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    status: state.authentication.status,
    isLoggedIn: state.authentication.status.isLoggedIn,
    currentUser: state.authentication.status.currentUser,
    memoData: state.memo.list.data
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
      memoListRequest: (isInitial, listType, id, username) => {
          return dispatch(memoListRequest(isInitial, listType, id, username));
      }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Home);