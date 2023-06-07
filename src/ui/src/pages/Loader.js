import '../pages/Loader.css';
import React from 'react';
import {Link} from 'react-router-dom';
import loaderGif from '../assets/artifact.gif';

const Loader = () => {
    return (
        <>
        <div className={'loader-div'}>
                <img width={'300px'} height={'300px'} src={loaderGif}/>
                <p>
                    <Link to={'https://giphy.com/gifs/ai-security-scanning-F6O1O2saVuBbYCfZhm'}>
                        via GIPHY
                    </Link>
                </p>
            </div>
        </>
    )
};

export default Loader;