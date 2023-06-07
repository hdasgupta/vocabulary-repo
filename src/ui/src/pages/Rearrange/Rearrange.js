import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import useFetch, {withPutFetch, withPostFetch} from '../../fetches/FetchChangeOneLetter';
import RearrangeInput from '../../pages/Rearrange/RearrangeInput'
import RearrangeMatch from '../../pages/Rearrange/RearrangeMatch'
import Loader from '../../pages/Loader';

const Rearrange = (props) => {
    let [properties, setProperties] = useState(props)
    const [closed, setClosed] = useState(props.closed)

    const handleChange = (word) => {
        if(!!word && !!props && word.length==props.chars.length) {
            properties = {};

            withPutFetch(
                '/api/games/rearrange/add/'+props.id+'/'+word,
                {},
                setProperties,
                ()=>{return true;}
            )

        }
    };

    const tryClose = () => {
        withPostFetch(
            '/api/games/rearrange/close/'+props.id,
            {},
            setClosed,
            ()=>{return true;}
        )
    }

    if(!!properties && properties.points>=0) {

        return (
            <center>
                <h3>Word Size: {properties.chars.length} | Attempts: {properties.guessedWords.length} | Points: {properties.points}</h3>

                <table>
                    <tbody>
                        <tr>
                            <td style={{verticalAlign: 'top'}} align="center">
                            {
                                properties.closed || closed ?
                                (
                                    <>
                                    {
                                        'Closed!!'
                                    }
                                    </>
                                )
                                :
                                (
                                    <>
                                        <Button variant="primary" onClick={(e)=>tryClose()} disabled={!properties.closable}>
                                            Close
                                        </Button>
                                        <RearrangeInput {...properties} onSelect={handleChange} />
                                    </>
                                )

                            }

                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <RearrangeMatch guessedWords={properties.guessedWords} />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </center>
        );
    }
    else
        return (
            <>
            </>
        );

};

export default Rearrange;