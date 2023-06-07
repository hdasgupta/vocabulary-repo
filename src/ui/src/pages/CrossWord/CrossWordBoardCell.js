import React, {useState} from 'react';
import {range} from '../../common/Methods';
import '../../pages/CrossWord/CrossWordBoardCell.css'

const CrossWordBoardCell = (props) => {
    const [selected, setSelected] = useState(props.selected)
    let [className, setClassName] = useState(props.readOnly?"CellReadOnly":(props.selected?"CellSelected":"CellUnselected"))

    const onHover = (e) => {
        if(!selected) {
            setClassName("CellHover")
        }
    }

    const onHoverOut = (e) => {
        setClassName("CellUnselected")
    }

    const onClick = (e) => {
        props.onSelect(props.row, props.column);
        setClassName("CellSelected");
    }

    return props.readOnly?
        (
            <td className={["CellDefault", className]} align="center" valign="middle">
            {
                props.input
            }
            </td>
        ):
        (
            <td className={["CellDefault", className]} align="center" valign="middle"
                onMouseEnter={onHover}
                onMouseOut={onHoverOut}
                onClick={onClick}
            >
            {
                props.input
            }
            </td>
        )

}

export default CrossWordBoardCell;
