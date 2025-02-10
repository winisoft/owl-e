import React from "react";
import Icon from "../Icon";

interface FolderStatProps {
    icon: string;
    text: string;
  }
  
  const FolderStat: React.FC<FolderStatProps> = ({ icon, text }) => {
    return (
        <div className="flex items-center">
            <div className="pl-4 pr-4">
            <Icon icon={icon} />
            </div>

            <span className="text-lg text-gray-400">{text}</span>
        </div>
    );
  }

export default FolderStat;