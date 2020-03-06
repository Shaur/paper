class IssueApi {

    static issues(date) {

    }

    static issuesToday() {
        return fetch(`${process.env.REACT_APP_URL}/issue`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        });
    }
    
    static subscribe(issueId, subscription) {
        return fetch(`${process.env.REACT_APP_URL}/issue/${issueId}?subscribe=${subscription}`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        }).catch((error) => {
            this.props.history.push("/login")
        });
    }
}

export default IssueApi