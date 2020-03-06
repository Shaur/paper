import React from 'react';
import './App.css';
import Page from "./Page";
import ReaderApi from './api/ReaderApi'

class Reader extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            sources: [],
            index: 0,
            series: props.match.params.series,
            number: props.match.params.number
        };
    }
    
    componentDidMount() {
        ReaderApi.pages(this.state.series, this.state.number)
            .then(pages => this.setState({sources: pages}))
    }

    onNext = () => {
        const hasNext = this.state.index < this.state.sources.length - 1;
        if (hasNext) {
            this.setState({
                index: this.state.index + 1
            });
        }
    };

    onPrev = () => {
        const hasPrev = this.state.index > 0;
        if (hasPrev) {
            this.setState({
                index: this.state.index - 1
            });
        }
    };

    render() {
        const src = this.state.sources[this.state.index];

        return (
            <Page src={`${process.env.REACT_APP_URL}` + src}
                  current={this.state.index + 1}
                  total={this.state.sources.length}
                  onNext={this.onNext}
                  onPrev={this.onPrev}/>
        );
    }
}

export default Reader