import React, { useState } from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import ElementHeader from '../Element/ElementHeader';
import ElementPanel from '../Element/ElementPanel';

interface DataPointStatProps {
    header: React.ReactNode;
    items: React.ReactNode[];
    onItemsChange: () => void;
}

const DataPointStat: React.FC<DataPointStatProps> = ({ header, items, onItemsChange}) => {
    const [isHovered, setIsHovered] = useState<boolean>(false);
    return (
        <div
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <ElementHeader 
                icon={Icons.Data}
                text={<h2 className="flex-grow truncate text-xl font-bold text-white">{items.length} Data Points</h2>}
                panel={
                <ElementPanel 
                    elementPanelBtns={
                    [

                    ]} 
                    isVisible={isHovered}
                />}
            />
        </div>
    );
  }

export default DataPointStat;