import React from "react"
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';

import axios from 'axios'

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: ''
        }
    }

    handleClick = (event) => {
        let apiBaseUrl = `${process.env.REACT_APP_URL}/users/sessions`;
        // let self = this;
        let payload = {
            userName: this.state.userName,
            password: this.state.password
        }

        axios.post(apiBaseUrl, payload)
            .then((resp) => {
                console.log(resp);
                if (resp.status === 200) {
                    alert("Auth");
                } else if (resp.status === 204) {
                    alert("username password do not match");
                } else {
                    console.log("Username does not exists");
                    alert("Username does not exist");
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }

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
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Login