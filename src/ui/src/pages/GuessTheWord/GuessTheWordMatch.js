import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import useFetch, {withPutFetch} from '../../fetches/FetchChangeOneLetter';
import GuessTheWordInput from '../../pages/GuessTheWord/GuessTheWordInput'
import GuessTheWordGuessMap from '../../pages/GuessTheWord/GuessTheWordGuessMap'
import {nextGuessType, getVariant, getVariantByLabel, getAllLabels} from '../../pages/GuessTheWord/Common'
import Loader from '../../pages/Loader';
import '../../pages/GuessTheWord/GuessTheWord.css';

const guessTypeNamesVariant = {
   'NoIdea': 'light',
   'Doubt': 'warning',
   'ContainsSurely' : 'success',
   'NotContainsSurely': 'danger',
}

const GuessTheWordMatch = (props) => {
    const {guessedWords, chart, onChartChanged, closed} = props;

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
            {
                guessedWords.map(match=> {
                    return (
                        <tr>
                        {
                            Array.from(match.word).map(chr=> {
                                return (
                                    <td align="center">
                                    {
                                        closed?
                                        (
                                            <>
                                            {
                                                chr
                                            }
                                            </>
                                        ):
                                        (
                                            <Button variant={getVariant(chart[chr])} onClick={(e)=>changeColor(e, chr)}>
                                            {
                                                chr
                                            }
                                            </Button>
                                        )

                                    }

                                    </td>
                                )
                            })
                        }
                            <td>{'👍'.repeat(match.match)}</td>
                        </tr>
                    )
                })
            }
            </tbody>
        </table>
    );

};

export default GuessTheWordMatch;