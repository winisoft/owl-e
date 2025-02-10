import React, { useState, useEffect, useRef } from "react";
import Icons from "../Icons";
import IconButton from "../IconButton";
import Icon from "../Icon";

interface TextFieldProps {
  onQueryTextChange: (text: string) => void;
  setFilterIsOn: (isOn: boolean) => void;
  setListIsExpanded: (isExpanded: boolean) => void
  hintText: string;
}

const DataPointsSearchTextField: React.FC<TextFieldProps> = ({ onQueryTextChange, setFilterIsOn, setListIsExpanded, hintText }) => {
    const [isHovered, setIsHovered] = useState<boolean>(false);
    const [text, setText] = useState("");
    const inputRef = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  const handleFilterOff = () => {
    setText("");
    onQueryTextChange("");
    setFilterIsOn(false);
  };

  const handleListCollapse = () => {
    setText("");
    onQueryTextChange("");
    setListIsExpanded(false);
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newText = e.target.value;
    setText(newText);
    onQueryTextChange(newText);
  };

  const handleClear = () => {
    setText("");
    onQueryTextChange("");
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  return (
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
        placeholder={text ? "" : hintText}
        className="text-3xl font-bold text-white bg-transparent border-none outline-none caret-orange-500 flex-grow" 
      />
      <IconButton icon={Icons.FilterOff} onClick={handleFilterOff} size={40} className="w-6 h-6 text-white" />
    </div>
  );
  
};

export default DataPointsSearchTextField;