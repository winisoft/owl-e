import React, { useState } from 'react';
import IconButton from '../IconButton';
import Icons from '../Icons';
import Icon from '../Icon';
import { OwleIcon } from './OwleIcon';

interface OwleHeaderProps {
  newWorkflowClick: () => void;
  filterWorkflowsClick: () => void;
  settingsClick: () => void;
  showTagChips: () => void;
}

const OwleHeader: React.FC<OwleHeaderProps> = ({ newWorkflowClick, filterWorkflowsClick, settingsClick, showTagChips }) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      className="gap-2 w-full pb-6" 
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div className="flex justify-between items-center mb-4">
          <div className={"pl-2 pr-4"}>
              <OwleIcon icon={Icons.Owle} />
          </div>
            
          <h1 className="text-4xl flex-grow font-bold text-white">OWL-E</h1>

          <div className="flex truncate"/>
            {isHovered && (
              <div className="w-auto h-auto flex space-x-4">
                <IconButton icon={Icons.Filter} onClick={showTagChips} className="w-10 h-10" />
                <IconButton icon={Icons.AddItem} onClick={newWorkflowClick} className="w-10 h-10" />
                <IconButton icon={Icons.Settings} onClick={settingsClick} className="w-10 h-10" />
              </div>
            )}
          </div>
        </div>

  );
};

export default OwleHeader;
