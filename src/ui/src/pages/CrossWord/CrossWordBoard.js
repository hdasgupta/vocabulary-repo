import React, {useState} from 'react';
import {range} from '../../common/Methods';
import CrossWordBoardCell from '../../pages/CrossWord/CrossWordBoardCell';
import CrossWordBoardNoCell from '../../pages/CrossWord/CrossWordBoardNoCell';

const CrossWordBoard = (props) => {
    const [properties, setProperties] = useState(props)
    const [array, setArray] = useState(buildArray(properties))

    const onSelect= (row, column) => {
        array.forEach((_, rowIndex) => {
            array[rowIndex].forEach((_, columnIndex) => {
                if(rowIndex !== row || columnIndex !== column) {
                    array[rowIndex][columnIndex].selected = false
                } else {
                    array[rowIndex][columnIndex].selected = true
                }
            })
        })
        setArray(array)
    }

    return (
        <table>
            <tbody>
            {
                array.map((row, rowIndex) => {
                    return (
                        <tr>
                        {
                            row.map((column, columnIndex)=> {
                                return column.writable?
                                    (
                                        <CrossWordBoardCell
                                            row={rowIndex}
                                            column={columnIndex}
                                            input={column.input}
                                            selected={column.selected}
                                            onSelect={onSelect}
                                            readOnly={!!props.readOnly || !column.writeable}
                                        />
                                    ):
                                    (
                                        <CrossWordBoardNoCell />
                                    )
                            })
                        }
                        </tr>
                    )
                })
            }
            </tbody>
        </table>
    )
}

function buildArray(properties) {
    const array = range(0, properties.size.rowa -1).map(
        _ => range(0, properties.size.columns -1).map(
            _=> {
                return {
                    index: null,
                    input: null,
                    selected:false,
                    writeable: false
                }
            }
        )
    )

    properties.inputs.forEach(
        input => {
            array[input.row][input.column].index = input.index
            array[input.row][input.column].input = input.input
            array[input.row][input.column].writeable = true
        }
    )

    return array;
}

export default CrossWordBoard;
