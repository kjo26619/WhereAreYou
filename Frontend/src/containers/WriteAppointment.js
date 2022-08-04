import React from 'react';
import { connect } from 'react-redux';
import { Write } from '../components';
import { memoPostRequest } from '../actions/memo';

class WriteAppointment extends React.Component {
    constructor(props) {
        super(props);        
        this.handlePost = this.handlePost.bind(this);
    }

    /* POST MEMO */
    handlePost(contents) {
        return this.props.memoPostRequest(contents).then(
            () => {
                if(this.props.postStatus.status === "SUCCESS") {
                    // TRIGGER LOAD NEW MEMO
                    // TO BE IMPLEMENTED
                    console.log('[MemoPostRequest] success!');
                } else {
                    /*
                        ERROR CODES
                            1: NOT LOGGED IN
                            2: EMPTY CONTENTS
                    */
                    switch(this.props.postStatus.error) {
                        case 1:
                            // IF NOT LOGGED IN, NOTIFY AND REFRESH AFTER
                            console.log('You are not logged in');
                            break;
                        case 2:
                            console.log('Please write something');
                            break;
                        default:
                            console.log('Something Broke');
                            break;
                    }
                }
            }
        );
    }


    render() {
        return (
            <div className="wrapper">
                <Write onPost={this.handlePost}/>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        isLoggedIn: state.authentication.status.isLoggedIn,
        postStatus: state.memo.post
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        memoPostRequest: (contents) => {
            return dispatch(memoPostRequest(contents));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(WriteAppointment);