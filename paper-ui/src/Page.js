import React from "react";

class Page extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            onPrev: props.onPrev,
            onNext: props.onNext
        }
    }

    render() {
        return (
            <div className={"reader"}>
                <div className={"pageCounter"}>
                    <label>{`${this.props.current}/${this.props.total}`}</label>
                </div>
                <img src={this.props.src} alt={"Test"} className={"page"} onClick={this.state.onNext}/>
                <br/>
                <button onClick={this.state.onPrev} disabled={this.props.current === 1}>prev</button>
                <button onClick={this.state.onNext} disabled={this.props.current === this.props.total}>next</button>
            </div>
        );
    }
}

export default Page