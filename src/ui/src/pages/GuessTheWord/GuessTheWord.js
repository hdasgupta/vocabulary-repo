import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import TextField from '@mui/material/TextField';
import useFetch, {withPutFetch} from '../../fetches/FetchChangeOneLetter';
import GuessTheWordInput from '../../pages/GuessTheWord/GuessTheWordInput'
import GuessTheWordMatch from '../../pages/GuessTheWord/GuessTheWordMatch'
import GuessTheWordGuessMap from '../../pages/GuessTheWord/GuessTheWordGuessMap'
import Loader from '../../pages/Loader';
import '../../pages/GuessTheWord/GuessTheWord.css';

const GuessTheWord = (props) => {
    let [properties, setProperties] = useState(props)
    let [chart, setChart] = useState(undefined)

    const handleChange = (word) => {
        if(!!word && !!props && word.length==props.wordLength) {
            properties = {};
            chart = undefined;

            withPutFetch(
                '/api/games/guess-the-word/add/'+props.id+'/'+word,
                {},
                setProperties,
                ()=>{return true;}
            )

        }
    };

    if(!!properties && !!properties.point) {
        if(!chart) {
            chart = properties.guessChart
        }

        return (
            <center>
                <h3>Word Size: {properties.wordLength} | Attempts: {properties.attempts} | Points: {properties.point}</h3>

                <table>
                    <tbody>
                        <tr>
                            <td style={{verticalAlign: 'top'}}>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td>
                                            {
                                                (properties.isClosed)?
                                                (
                                                    <h4>Correctly Guessed!!</h4>
                                                )
                                                :
                                                (
                                                    <GuessTheWordInput {...properties} onSelect={handleChange}/>
                                                )

                                            }

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <GuessTheWordMatch closed={properties.isClosed} id={properties.id} guessedWords={properties.guessedWords} chart={chart} onChartChanged={setChart} />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td width="20">
                            </td>
                            {
                                (!properties.isClosed)?
                                (
                                    <td width="100" style={{verticalAlign: 'top'}}>
                                        <GuessTheWordGuessMap id={props.id} chart={chart} onChartChanged={setChart} />
                                    </td>
                                )
                                :
                                (
                                    <>
                                    </>
                                )
                            }
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

export default GuessTheWord;