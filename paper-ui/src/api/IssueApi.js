class IssueApi {

    static issues(date) {

    }

    static issuesToday() {
        return fetch(`${process.env.REACT_APP_URL}/issue`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        }).then(response => {
            if (response.status === 200)
                return response.json();
            else
                throw response.json().then;
        });
    }
    
    static subscribe(issueId, subscription) {
        return fetch(`${process.env.REACT_APP_URL}/issue/${issueId}?subscribe=${subscription}`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            }
        }).then(resp => {
            if(resp.status !== 200)
                throw resp.json();
        });
    }
}

export default IssueApi