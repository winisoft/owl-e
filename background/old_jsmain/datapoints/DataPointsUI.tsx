import React, { useEffect, useRef, useState } from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import DataPointDLL from '../../dll/DataPointDLL';
import DataPointCard from './DataPointCard';
import Icon from '../Icon';
import { DataPoint, NewDataPoint } from '../../models';


interface DataPointsProps {
  workflowId: number;
  dataPoints: DataPoint[];
}


const DataPointsUI: React.FC<DataPointsProps> = ({ workflowId, dataPoints }) => {
  const [isListExpanded, setListExpanded] = useState<boolean>(false);
  const [searchQuery, setSearchQuery] = useState<string>("");
  const [showEditingDataPoint, setEditingDataPointShown] = useState<boolean>(false);
  const handleNewDataPointClick = () => {};
  
  const handleSearchClick = () => {};
  
  const toggleExpandedClick = () => {
    setListExpanded(!isListExpanded);
  };

  const filteredDataPoints = searchQuery === "" 
    ? dataPoints 
    : dataPoints.filter((dataPoint: DataPoint) =>
        dataPoint.name.toLowerCase().includes(searchQuery.toLowerCase())
      );

  const handleQueryTextChange = (queryText: string) => {
    setSearchQuery(queryText);
  };

  const handleCommitEdit = (edited: NewDataPoint) => {
    new DataPointDLL().createDataPoint(edited);
  };

  const [isHovered, setIsHovered] = useState<boolean>(false);
  const [isSearching, setIsSearching] = useState<boolean>(false);
  
  const handleSetExpanded = (isExpanded: boolean) => {};

  const [text, setText] = useState("");
  const inputRef = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  const handleFilterOff = () => {
    setText("");
    handleQueryTextChange("");
    setIsSearching(false);
  };

  const handleListCollapse = () => {
    setText("");
    handleQueryTextChange("");
    setListExpanded(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newText = e.target.value;
    setText(newText);
    handleQueryTextChange(newText);
  };

  const handleClear = () => {
    setText("");
    handleQueryTextChange("");
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  // Main UI Rendering
  return (
    <div className="shadow-lg rounded-lg pl-6 pr-6 pb-6" >
      <div
      onClick={() => !isSearching && toggleExpandedClick()}
        className="flex justify-between items-center ml-8 mr-8 pt-4 pb-4 rounded transition-all duration-200 ease-in-out bg-gray-800 hover:bg-gray-700 active:bg-gray-600 rounded-md"
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        {isSearching ? (
          <div
            className="flex justify-between items-center ml-8 mr-8 pt-4 pb-4 pr-4 rounded transition-all duration-200 ease-in-out bg-gray-800 hover:bg-gray-700 active:bg-gray-600 rounded-md space-x-4 w-full"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
          >
            <div className="pl-6 pr-6">
              <Icon icon={Icons.DataItemSearch} size={40} className="w-6 h-6 text-white" />
            </div>
            <input
              ref={inputRef}
              type="text"
              value={text}
              onChange={handleChange}
              placeholder={text ? "" : `${dataPoints.length} Data Points`}
              className="text-3xl font-bold text-white bg-transparent border-none outline-none caret-orange-500 flex-grow"
            />
            <IconButton
              icon={Icons.FilterOff}
              onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                e.stopPropagation(); // Prevent click from collapsing list
                handleFilterOff();
              }}
              size={40}
              className="w-6 h-6 text-white"
            />
          </div>
        ) : (
          <div className="flex items-center space-x-4 w-full">
            <div className="flex items-center space-x-2 flex-grow">
              <div className="pl-6 pr-6">
                <Icon icon={Icons.Data} size={40} className="w-6 h-6 text-white" />
              </div>
              <h1 className="text-3xl font-bold text-white">{dataPoints.length} Data Points</h1>
            </div>

            {isHovered && (
              <div className="w-auto h-auto flex space-x-4 pr-6">
                <IconButton
                  icon={Icons.DataItemAdd}
                  size={40}
                  onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                    e.stopPropagation(); // Prevent click from closing list
                    setEditingDataPointShown(true);
                  }}
                />
                <IconButton
                  icon={Icons.DataItemSearch}
                  size={40}
                  onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                    e.stopPropagation(); // Prevent click from closing list
                    setIsSearching(true);
                  }}
                />
              </div>
            )}
          </div>
        )}
      </div>

      {isListExpanded && (
        <div className="pl-10 pr-10 pt-4 pb-4">
          {filteredDataPoints.map((dataPoint: DataPoint) => (
            <DataPointCard workflowId={workflowId} dataPoint={dataPoint} onEdit={() => {}} onDelete={() => {}} />
          ))}
        </div>
      )}
    </div>
  );
};

export default DataPointsUI;

