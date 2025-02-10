import React, { useState } from "react";
import Icons from "./Icons";
import Icon from "./Icon";
import IconButton from "./IconButton";
import { Step } from "../models";

interface NestedStepProps {
    step: Step;
    onExpand: () => void;
}

const NestedStep: React.FC<NestedStepProps> = ({ step, onExpand }) => {
    return (
        <div className="p-4 workflow bg-gray-700 rounded-lg shadow-md h-full"> {/* Add h-full to ensure height adjusts */}
            <div className="flex justify-between items-center mb-4">
                <div className="pr-2">
                    <Icon icon={Icons.Step} size={30} className="w-6 h-6" />
                </div>
                 {/* Adjust width/height */}
                <h2 className="text-xl font-semibold text-white flex-grow">{step.name}</h2> {/* Add flex-grow */}
                <div>
                    <IconButton icon={Icons.Clone} onClick={() => {}} />
                </div>
            </div>
            {step.description && (
                <p className="text-gray-400 mb-2">{step.description}</p>
            )}
            <div className="flex justify-between items-center mb-4">
                <div className="p-4">
                    <Icon icon={Icons.Function} size={25} className="w-6 h-6" />
                </div>
                 {/* Adjust width/height */}
                <p className="code-style flex-grow">{step.tool.name}</p> {/* Add flex-grow */}
            </div>
            <p className=" mb-2"></p>
            {step.isWorkflowHead && (
                <p className="text-sm text-green-300">This is the workflow head</p>
            )}
        </div>
    );
}

export default NestedStep;
