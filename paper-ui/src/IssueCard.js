import React from "react";
import IssueApi from "./api/IssueApi";

class IssueCard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            series: props.issue.seriesId,
            issueId: props.issue.id,
            name: props.issue.name,
            number: props.issue.number,
            subscribe: props.issue.subscribe
        };

        this.onSubscribe = this.onSubscribe.bind(this);
    }

    onSubscribe(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        IssueApi.subscribe(this.state.issueId, value)
            .then(() => this.setState({subscribe: value}));

    }

    render() {
        return (
            <tr>
                <td className="subscribe-col">
                    <input name="subscribe" type="checkbox" checked={this.state.subscribe}
                           onChange={this.onSubscribe}/>
                </td>
                <td>
                        <a href={`/read/${this.state.series}/${this.state.issueId}`}>{`${this.state.name} #${this.state.number}`}</a>
                </td>
            </tr>
        )
    }
}

export default IssueCard