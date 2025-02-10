import React, { useState } from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import Icon from '../Icon';

interface DataPointBreakdownProps {
    isReadonly: boolean;
    // provisions: DataPointProvision[];
    // revisions: DataPointRevision[];
    // inquisitions: DataPointInquisition[];
  }
  
  const DataPointBreakdown: React.FC<DataPointBreakdownProps> = ({
    isReadonly,
    // provisions,
    // revisions,
    // inquisitions,
  }) => {
    const provisions = [];
    const revisions = [];
    const inquisitions = [];
    const [isProvisionsHovered, setIsProvisionsHovered] = useState<boolean>(false);
    const [isRevisionsHovered, setIsRevisionsHovered] = useState<boolean>(false);
    const [isInquisitionsHovered, setIsInquisitionsHovered] = useState<boolean>(false);
    const [isProvisionsExpanded, setIsProvisionsExpanded] = useState<boolean>(false);
    const [isRevisionsExpanded, setIsRevisionsExpanded] = useState<boolean>(false);
    const [isInquisitionsExpanded, setIsInquisitionsExpanded] = useState<boolean>(false);
  
    return (
      <div className="flex flex-col space-y-2">
        {/* Provisions */}
        <div
          onMouseEnter={() => setIsProvisionsHovered(true)}
          onMouseLeave={() => setIsProvisionsHovered(false)}
          className="flex items-center justify-between p-2 rounded transition-all duration-200 hover:bg-gray-700 active:bg-gray-600"
        >
          <div className="flex items-center space-x-2">
            <Icon icon={Icons.Provisions} className="w-6 h-6" /> {/* Consistent icon size */}
            <p className="text-lg text-gray-300">Provided to {provisions.length} {provisions.length === 1 ? 'Step' : 'Steps'}</p> {/* Larger text */}
          </div>
          {isProvisionsHovered && (
            <div className="flex space-x-2">
              <IconButton size={24} icon={Icons.AddItem} className="w-6 h-6" onClick={() => setIsProvisionsExpanded(!isProvisionsExpanded)} />
              <IconButton size={24} icon={isProvisionsExpanded ? Icons.CollapseSection : Icons.ExpandSection} className="w-6 h-6" onClick={() => setIsProvisionsExpanded(!isProvisionsExpanded)} />
            </div>
          )}
        </div>
    
        {/* Revisions */}
        {!isReadonly && (
          <div
            onMouseEnter={() => setIsRevisionsHovered(true)}
            onMouseLeave={() => setIsRevisionsHovered(false)}
            className="flex items-center justify-between p-2 rounded transition-all duration-200 hover:bg-gray-700 active:bg-gray-600"
          >
            <div className="flex items-center space-x-2">
              <Icon icon={Icons.Revisions} className="w-6 h-6" /> {/* Consistent icon size */}
              <p className="text-lg text-gray-300">Assigned by {revisions.length} {revisions.length === 1 ? 'Step' : 'Steps'}</p>
            </div>
            {isRevisionsHovered && (
              <div className="flex space-x-2">
                <IconButton size={24} icon={Icons.AddItem} className="w-6 h-6" onClick={() => setIsRevisionsExpanded(!isRevisionsExpanded)} />
                <IconButton size={24} icon={isRevisionsExpanded ? Icons.CollapseSection : Icons.ExpandSection} className="w-6 h-6" onClick={() => setIsRevisionsExpanded(!isRevisionsExpanded)} />
              </div>
            )}
          </div>
        )}
    
        {/* Inquisitions */}
        <div
          onMouseEnter={() => setIsInquisitionsHovered(true)}
          onMouseLeave={() => setIsInquisitionsHovered(false)}
          className="flex items-center justify-between p-2 rounded transition-all duration-200 hover:bg-gray-700 active:bg-gray-600"
        >
          <div className="flex items-center space-x-2">
            <Icon icon={Icons.Inspectors} className="w-6 h-6" /> {/* Consistent icon size */}
            <p className="text-lg text-gray-300">
              {inquisitions.length > 0 ? `Redirects in ${inquisitions.length} ${inquisitions.length === 1 ? 'Case' : 'Cases'}` : 'Never inspected'}
            </p>
          </div>
          {isInquisitionsHovered && (
            <div className="flex space-x-2">
              <IconButton size={24} icon={Icons.AddItem} className="w-6 h-6" onClick={() => setIsInquisitionsExpanded(!isInquisitionsExpanded)} />
              <IconButton size={24} icon={isInquisitionsExpanded ? Icons.CollapseSection : Icons.ExpandSection} className="w-6 h-6" onClick={() => setIsInquisitionsExpanded(!isInquisitionsExpanded)} />
            </div>
          )}
        </div>
      </div>
    );
    
  };
  
  export default DataPointBreakdown;
  