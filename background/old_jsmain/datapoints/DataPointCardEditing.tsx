import React, { useState } from 'react';
import Icons from '../Icons';
import ElementPanel from '../Element/ElementPanel';
import IconButton from '../IconButton';
import DataPointBreakdownEdit from './DataPointBreakdownEdit';
import Icon from '../Icon';
import { NewInquisition, NewProvision, NewRevision } from '../../models';

interface DataPointCardEditingProps {
  initialName?: string;
  initialType?: string;
  initialDescription?: string;
  initialReadOnly?: boolean;
  initialValue?: string;
  initialProvisions: NewProvision[];
  initialRevisions: NewRevision[];
  initialInquisitions: NewInquisition[];
  onSave: (name: string, type: string, description: string, readOnly: boolean, value: string, 
    provisions: NewProvision[], revisions: NewRevision[], inquisitions: NewInquisition[]) => void;
  onDelete: () => void;
}

const DataPointCardEditing: React.FC<DataPointCardEditingProps> = ({
  initialName = 'New Data Point',
  initialType = '',
  initialDescription = '',
  initialReadOnly = false,
  initialValue = '',
  initialProvisions = [],
  initialRevisions = [],
  initialInquisitions = [],
  onSave,
  onDelete,
}) => {
  const [name, setName] = useState(initialName);
  const [type, setType] = useState(initialType);
  const [description, setDescription] = useState(initialDescription);
  const [isReadOnly, setIsReadOnly] = useState(initialReadOnly);
  const [presetValue, setPresetValue] = useState(initialValue);
  const [provisions, setProvisions] = useState(initialProvisions);
  const [revisions, setRevisions] = useState(initialRevisions);
  const [inquisitions, setInquisitions] = useState(initialInquisitions);
  const [focused, setFocused] = useState(false);

  const handleSave = () => {
    onSave(name, type, description, isReadOnly, presetValue, provisions, revisions, inquisitions);
  };

  return (
    <div
      className={`relative p-4 rounded-lg bg-gray-800 text-white border-2 ${
        focused ? 'border-green-400' : 'border-gray-600'
      } hover:border-gray-400 transition duration-200`}
      onFocus={() => setFocused(true)}
      onBlur={() => setFocused(false)}
      tabIndex={0}
    >
      <div className="flex items-center justify-between mb-2">
        <Icon icon={Icons.DataItem} />
        <input
          type="text"
          className="flex-grow bg-transparent border-b-2 border-gray-600 focus:border-green-400 text-xl font-bold outline-none mr-2"
          placeholder="New Data Point"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <ElementPanel 
            elementPanelBtns={[
                
                <div>{name && type && ( <IconButton icon={Icons.Check} onClick={handleSave} />)}</div>,
                <IconButton icon={Icons.Trash} onClick={onDelete} />
            ]}
            isVisible={true}
        />
      </div>

      <div className="flex items-center space-x-2 mb-2">
        <input
          type="text"
          className="flex-grow bg-transparent border-b-2 border-gray-600 focus:border-green-400 outline-none"
          placeholder="Select a data type..."
          value={type}
          onChange={(e) => setType(e.target.value)}
        />
        <Icon icon={Icons.DataType} className="w-6 h-6 text-green-400" />
      </div>

      <textarea
        className="w-full bg-transparent border-b-2 border-gray-600 focus:border-orange-400 outline-none"
        placeholder="Description..."
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <div className="flex items-center space-x-2 mt-2">
        <input
          type="checkbox"
          className="form-checkbox"
          checked={isReadOnly}
          onChange={() => setIsReadOnly(!isReadOnly)}
        />
        <label className="text-sm text-gray-400">Is 'Read Only'</label>
      </div>

      {isReadOnly && (
        <input
          type="text"
          className="w-full bg-transparent border-b-2 border-gray-600 focus:border-green-400 outline-none mt-2"
          placeholder="Read-only value"
          value={description}
          disabled
        />
      )}
      <DataPointBreakdownEdit 
        provisions={provisions} 
        revisions={revisions} 
        inquisitions={inquisitions} 
        setProvisions={setProvisions} 
        setRevisions={setRevisions} 
        setInquisitions={setInquisitions}       
       />
    </div>
  );
};

export default DataPointCardEditing;
