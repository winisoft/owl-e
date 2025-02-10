import React from 'react';
import Icons from '../Icons';
import IconButton from '../IconButton';
import DataPointBreakdown from './DataPointBreakdown';
import Icon from '../Icon';
import { DataPoint } from '../../models';

interface DataPointCardProps {
  workflowId: number;
  dataPoint: DataPoint;
  onEdit: (dataPoint: DataPoint) => void;
  onDelete: (dataPoint: DataPoint) => void;
}

const DataPointCard: React.FC<DataPointCardProps> = ({
  workflowId,
  dataPoint,
  onEdit,
  onDelete
}) => {

  return (
    <div className="bg-black rounded-lg p-6 mb-4 shadow-lg w-full" tabIndex={0}>
      {/* Header section with name, edit, and delete buttons */}
      <div className="flex items-center justify-between mb-4">
        <div className="flex items-center space-x-4">
          <Icon
            size={35}
            icon={dataPoint.isReadOnly ? Icons.DataItemReadonly : Icons.DataItem}
            className="w-8 h-8 text-white"
          />
          <h1 className="text-3xl font-bold text-white">{dataPoint.name}</h1>
        </div>
        <div className="flex space-x-2">
          <IconButton size={35} icon={Icons.DataItemEdit} onClick={() => onEdit(dataPoint)} />
          <IconButton size={35} icon={Icons.RemoveItem} onClick={() => onDelete(dataPoint)} />
        </div>
      </div>
  
      {/* Content section: split into two columns */}
      <div className="flex w-full space-x-4">
        {/* Left column: Type, Description, and Value */}
        <div className="w-3/5">
          <div className="text-2xl text-gray-300 mb-2">
            {dataPoint.typeDeclaration.typeDefinition.name}
          </div>
  
          {dataPoint.description && (
            <p className="text-xl text-orange-400 mb-2">{dataPoint.description}</p>
          )}
  
          {!dataPoint.isReadOnly && (
            <p className="text-xl text-blue-400 mb-2">{dataPoint.textValue ? dataPoint.textValue : ""}</p>
          )}
        </div>
  
        {/* Right column: Breakdown */}
        <div className="w-2/5">
          <DataPointBreakdown
            isReadonly={dataPoint.isReadOnly == true}
            // provisions={dataPoint.provisions}
            // revisions={dataPoint.revisions}
            // inquisitions={dataPoint.inquisitions}
          />
        </div>
      </div>
    </div>
  );
}

export default DataPointCard;
