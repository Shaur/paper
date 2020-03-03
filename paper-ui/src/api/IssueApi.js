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
}

export default IssueApi