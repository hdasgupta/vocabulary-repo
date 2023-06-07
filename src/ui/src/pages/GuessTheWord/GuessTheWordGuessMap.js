import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from 'react-bootstrap/Button';
import useFetch, {withPutFetch} from '../../fetches/FetchChangeOneLetter';
import GuessTheWordInput from '../../pages/GuessTheWord/GuessTheWordInput'
import {nextGuessType, getVariant, getVariantByLabel, getAllLabels} from '../../pages/GuessTheWord/Common'
import Loader from '../../pages/Loader';
import '../../pages/GuessTheWord/GuessTheWord.css';

const letters= [
    ['A', 'B', 'C', 'D', 'E'],
    ['F', 'G', 'H', 'I', 'J'],
    ['K', 'L', 'M', 'N', 'O'],
    ['P', 'Q', 'R', 'S', 'T'],
    ['U', 'V', 'W', 'X', 'Y'],
    ['', '', 'Z', '', '']
]

const GuessTheWordGuessMap = (props) => {
    const {chart, onChartChanged} = props;


    const changeColor = (e,letter) => {
        if(!!letter) {
            const guessType = nextGuessType(chart[letter]);

            console.log(guessType);

            withPutFetch(
                '/api/games/guess-the-word/guess-type/'+props.id+'/'+letter+'/'+guessType,
                {},
                onChartChanged,
                ()=>{return true;}
            )
        }
    }

    return (
        <table>
            <tbody>
                <tr>
                    <td colSpan="5" align="center" valign="middle">
                        Guess Map
                    </td>
                </tr>
            {
                letters.map(letterRow => {
                    return (
                        <tr>
                        {
                            letterRow.map(letter=>{
                                return (
                                    <td width="30" align="center">
                                    {
                                        !!letter?
                                        (
                                            <Button variant={getVariant(chart[letter])} onClick={(e)=>changeColor(e, letter)}>
                                            {
                                                letter
                                            }
                                            </Button>
                                        ):
                                        (
                                            <>
                                            </>
                                        )
                                    }
                                    </td>
                                );
                            })
                        }
                        </tr>
                    );
                })
            }
            {
                getAllLabels().map((guessTypeName, index) =>{
                    return (
                        <tr>
                            <td colSpan="5" style={{whiteSpace: 'nowrap'}} align="center">
                                <Button variant={getVariantByLabel(guessTypeName)} disabled>
                                {
                                    guessTypeName
                                }
                                </Button>
                            </td>
                        </tr>
                    );
                })
            }
            </tbody>
        </table>
    );

};

export default GuessTheWordGuessMap;