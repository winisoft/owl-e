import React, { useState } from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import DataPointsSearchTextField from './DataPointsSearchTextField';
import Icon from '../Icon';

interface DataPointsHeaderProps {
  dataPointsCount: number;
  isExpanded: boolean;
  onClick: () => void;
  dataSearchClick: () => void;
  setListIsVisible: (isVisible: boolean) => void;
  newDataPointClick: () => void;
  onQueryTextChange: (queryText: string) => void;
}

const DataPointsHeader: React.FC<DataPointsHeaderProps> = ({
  dataPointsCount,
  isExpanded,
  onClick,
  dataSearchClick,
  setListIsVisible,
  newDataPointClick,
  onQueryTextChange,
}) => {
  const [isHovered, setIsHovered] = useState<boolean>(false);
  const [isSearching, setIsSearching] = useState<boolean>(false);
  const handleSetExpanded = (isExpanded: boolean) => {
    setListIsVisible(isExpanded);
  }

  return (
    <div
      className="flex justify-between items-center ml-8 mr-8 pt-4 pb-4 rounded transition-all duration-200 ease-in-out bg-gray-800 hover:bg-gray-700 active:bg-gray-600 rounded-md" // Main container with padding and rounded corners
      onClick={() => {isSearching ? {} : onClick }}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      {isSearching ? (
        <DataPointsSearchTextField
          hintText={`${dataPointsCount} Data Points`}
          onQueryTextChange={onQueryTextChange}
          setFilterIsOn={setIsSearching}
          setListIsExpanded={setListIsVisible}
        />
      ) : (
        <div className="flex items-center space-x-4 w-full"> {/* Ensure the full width is used */}
          <div className="flex items-center space-x-2 flex-grow"> {/* Icon and data points count, flex-grow for text width expansion */}
            <div className="pl-6 pr-6">
                <Icon icon={Icons.Data} size={40} className="w-6 h-6 text-white" />
            </div>
            <h1 className="text-3xl font-bold text-white">{dataPointsCount} Data Points</h1>
          </div>

        {isHovered && (
            <div className="w-auto h-auto flex space-x-4 pr-6"> {/* Buttons */}
                <IconButton icon={Icons.DataItemAdd} size={40} onClick={newDataPointClick} />
                <IconButton icon={Icons.DataItemSearch} size={40} onClick={() => setIsSearching(true)} />
            </div>
        )}
        </div>
      )}
    </div>
  );
};

export default DataPointsHeader;
