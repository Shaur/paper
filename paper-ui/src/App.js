import React from 'react';

import './App.css';
import IssueCard from "./IssueCard";
import WeekDayPicker from "./WeekDayPicker"
import IssueApi from "./api/IssueApi"

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            issues: []
        };
    }

    componentDidMount() {
        IssueApi.issuesToday()
            .then(response => {
                if (!response.ok)
                    this.props.history.push('/login');
                return response.json();
            })
            .then(issues => this.setState({issues: issues}))
            .catch(error => {
                this.props.history.push('/login');
                console.log(error);
            })
    }

    dateChange = date => {
        fetch(`${process.env.REACT_APP_URL}/issue?date=${date.toISOString()}`)
            .then(response => response.json())
            .then(issues => this.setState({issues: issues}))
    };

    render() {
        return (
            <div>
                <WeekDayPicker handleChange={this.dateChange}/>
                <table className="table table-bordered table-dark">
                    <thead>
                    <tr>
                        <th className="subscribe-col">Subscribe</th>
                        <th>Weekly comics</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.issues.map(issue => (
                        <IssueCard key={issue.id} issue={issue}/>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default App