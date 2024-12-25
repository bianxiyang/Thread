package cn.com.tcsl.fast.kds.server.util;


import cn.com.tcsl.fast.kds.server.enums.StatusEnum;
import cn.com.tcsl.fast.kds.server.enums.UpdateKdsTypeEnum;
import cn.com.tcsl.fast.kds.server.service.bo.KdsStatusBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;


public class StatusUtil {
    private static final Logger logger = LoggerFactory.getLogger("kds");

    /**
     * <b>功能：</b>判断状态<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/30 10:34&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */

    public static Integer queryStatus(KdsStatusBo kdsStatusBo) {
        BigDecimal quantity = kdsStatusBo.getQuantity();
        BigDecimal finishQuantity = kdsStatusBo.getFinishQuantity().compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : kdsStatusBo.getFinishQuantity();
        BigDecimal transmitQuantity = kdsStatusBo.getTransmitQuantity().compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : kdsStatusBo.getTransmitQuantity();
        BigDecimal refundQuantity = kdsStatusBo.getRefundQuantity();
        BigDecimal usefulQuantity = quantity.subtract(refundQuantity);
        Integer updateKdsType = kdsStatusBo.getUpdateKdsType();
        boolean needMake = kdsStatusBo.isNeedMake();
        logger.info("品项操作:{} 是否需要制作:{} 原单数量:{}  制作完成数量:{} 传菜完成数量:{}  退单数量:{}",
                UpdateKdsTypeEnum.getEnumByValue(updateKdsType).getMessage(), needMake,
                usefulQuantity, finishQuantity, transmitQuantity, refundQuantity);
        switch (UpdateKdsTypeEnum.getEnumByValue(updateKdsType)) {
            //  1.单品完成,整单完成
            case SINGELE_FINISHED:
            case ORDER_FINISHED:
                if (usefulQuantity.compareTo(finishQuantity) == 0) {
                    return StatusEnum.NEW_MAKE_FINISH.getValue();
                } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    return StatusEnum.MAKING.getValue();
                }
                //  2.单品完成撤销,整单完成撤销
            case SINGLE_BACKOUT:
            case ORDER_BACKOUT:
                if (finishQuantity.compareTo(BigDecimal.ZERO) == 0) {
                    return StatusEnum.WAIT_MAKING.getValue();
                } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    return StatusEnum.MAKING.getValue();
                }
                break;
            //  3.单品传配，整单传配
            case SINGLE_TRANSMIT:
            case ORDER_TRANSMIT:
            case FORCE_OPERATION:
                //  全部制作，全部传
                if (usefulQuantity.compareTo(finishQuantity) == 0 && finishQuantity.compareTo(transmitQuantity) == 0) {
                    return StatusEnum.TRANSMIT_FINISH.getValue();
                } else if (usefulQuantity.compareTo(finishQuantity) == 0 && finishQuantity.compareTo(transmitQuantity) > 0) {
                    //  全部制作，部分传
                    return StatusEnum.TRANSMITING.getValue();
                } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    //  部分制作，传配
                    if (needMake) {
                        return StatusEnum.MAKING.getValue();
                    } else {
                        return usefulQuantity.compareTo(transmitQuantity) == 0 ? StatusEnum.TRANSMIT_FINISH.getValue() : StatusEnum.TRANSMITING.getValue();
                    }
                }
                break;
            //  4.单品传配撤销,整单传配撤销
            case SINGLE_TRANSMIT_BACKOUT:
            case ORDER_TRANSMIT_BACKOUT:
            case FORCE_OPERATION_BACKOUT:
                if (needMake) {
                    //  整单制作，整单撤销传配
                    if (usefulQuantity.compareTo(finishQuantity) == 0 && transmitQuantity.compareTo(BigDecimal.ZERO) == 0) {
                        return StatusEnum.NEW_MAKE_FINISH.getValue();
                    } else if (usefulQuantity.compareTo(finishQuantity) == 0 && finishQuantity.compareTo(transmitQuantity) > 0) {
                        //  整单制作，部分撤销传配
                        return StatusEnum.TRANSMITING.getValue();
                    } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                        //  部分制作，撤销部分传配
                        return StatusEnum.MAKING.getValue();
                    } else {
                        return StatusEnum.WAIT_MAKING.getValue();
                    }
                } else {
                    if (transmitQuantity.compareTo(BigDecimal.ZERO) == 0) {
                        if (finishQuantity.compareTo(BigDecimal.ZERO) == 0) {
                            //  未制作，直接传导致的撤销 900-->909--->900
                            return StatusEnum.WAIT_MAKING.getValue();
                        } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                            //  部分制作，直接传配的撤销  940--->944--->940
                            return StatusEnum.MAKING.getValue();
                        } else if (usefulQuantity.compareTo(finishQuantity) == 0) {
                            //  全部制作，直接传配的撤销  990--->999--->990
                            return StatusEnum.NEW_MAKE_FINISH.getValue();
                        }
                    } else {
                        if (usefulQuantity.compareTo(transmitQuantity) > 0) {
                            if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                                return StatusEnum.MAKING.getValue();
                            } else {
                                return StatusEnum.TRANSMITING.getValue();
                            }
                        }
                    }
                }
            case PART_OF_THE_REFUND:
                if (finishQuantity.compareTo(BigDecimal.ZERO) > 0 && usefulQuantity.compareTo(finishQuantity) == 0 && transmitQuantity.compareTo(BigDecimal.ZERO) == 0) {
                    //  全部制作，没有传菜
                    return StatusEnum.NEW_MAKE_FINISH.getValue();
                } else if (finishQuantity.compareTo(BigDecimal.ZERO) > 0 && usefulQuantity.compareTo(finishQuantity) == 0 && finishQuantity.compareTo(transmitQuantity) == 0) {
                    return StatusEnum.TRANSMIT_FINISH.getValue();
                } else if (finishQuantity.compareTo(BigDecimal.ZERO) > 0 && usefulQuantity.compareTo(finishQuantity) == 0 && finishQuantity.compareTo(transmitQuantity) > 0) {
                    //  全部制作，部分传
                    return StatusEnum.TRANSMITING.getValue();
                } else if (usefulQuantity.compareTo(finishQuantity) > 0 && finishQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    //  部分制作，传配
                    if (needMake) {
                        return StatusEnum.MAKING.getValue();
                    } else {
                        return usefulQuantity.compareTo(transmitQuantity) == 0 ? StatusEnum.TRANSMIT_FINISH.getValue() : StatusEnum.TRANSMITING.getValue();
                    }
                } else if(quantity.compareTo(refundQuantity)==0){
                    return StatusEnum.CANCEL_MAKING.getValue();
                }
                break;
        }
        return kdsStatusBo.getOriginStauts();
    }


}
