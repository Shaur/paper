import React from "react"
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';

import UserApi from './api/UserApi'

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
            error: ''
        }
    }

    handleClick = (event) => {
        UserApi.auth(this.state.userName, this.state.password)
            .then(() => {
                this.props.history.push('/');
            })
            .catch(error => this.setState({error: error.message}));
    };

    render() {
        return (
            <div>
                <MuiThemeProvider>
                    <div>
                        <TextField
                            hintText="Enter your Username"
                            floatingLabelText="Username"
                            onChange={(event, newValue) => this.setState({userName: newValue})}
                        />
                        <br/>
                        <TextField
                            type="password"
                            hintText="Enter your Password"
                            floatingLabelText="Password"
                            onChange={(event, newValue) => this.setState({password: newValue})}
                        />
                        <br/>
                        <RaisedButton label="Submit" primary={true} onClick={(event) => this.handleClick(event)}/>
                        <br/>
                        <label className={'label-danger'}>{this.state.error}</label>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Login