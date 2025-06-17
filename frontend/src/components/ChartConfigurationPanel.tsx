
import * as React from "react"
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { Checkbox } from "@/components/ui/checkbox"

export type Indicator = 'RSI' | 'MACD' | 'SMA';
export type ChartType = 'area' | 'line' | 'bar';
export type Algorithm = 'None' | 'Momentum' | 'MeanReversion';

export type ChartConfig = {
  id: string
  name: string
  symbol: string
  timeframe: string
  indicators: Indicator[]
  chartType: ChartType
  algorithm: Algorithm
}

interface ChartConfigurationPanelProps {
  config: ChartConfig
  onConfigChange: (newConfig: Partial<ChartConfig>) => void
}

const ChartConfigurationPanel = ({ config, onConfigChange }: ChartConfigurationPanelProps) => {
  const availableIndicators: Indicator[] = ['RSI', 'MACD', 'SMA'];
  const availableAlgorithms: Algorithm[] = ['None', 'Momentum', 'MeanReversion'];

  return (
    <Card className="h-full rounded-none border-0 border-l">
      <CardHeader>
        <CardTitle>Chart Settings</CardTitle>
        <CardDescription>Configure the active chart.</CardDescription>
      </CardHeader>
      <CardContent className="grid gap-6">
        <div className="grid gap-2">
          <Label htmlFor="symbol">Ticker Symbol</Label>
          <Input
            id="symbol"
            value={config.symbol}
            onChange={(e) => onConfigChange({ symbol: e.target.value.toUpperCase() })}
            placeholder="e.g. AAPL"
          />
        </div>
        <div className="grid gap-2">
          <Label htmlFor="timeframe">Timeframe</Label>
          <Select
            value={config.timeframe}
            onValueChange={(value) => onConfigChange({ timeframe: value })}
          >
            <SelectTrigger id="timeframe">
              <SelectValue placeholder="Select timeframe" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="1m">1 Minute</SelectItem>
              <SelectItem value="5m">5 Minutes</SelectItem>
              <SelectItem value="15m">15 Minutes</SelectItem>
              <SelectItem value="1H">1 Hour</SelectItem>
              <SelectItem value="4H">4 Hours</SelectItem>
              <SelectItem value="1D">1 Day</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <div className="grid gap-2">
          <Label htmlFor="chart-type">Chart Type</Label>
          <Select
            value={config.chartType}
            onValueChange={(value) => onConfigChange({ chartType: value as ChartType })}
          >
            <SelectTrigger id="chart-type">
              <SelectValue placeholder="Select chart type" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="area">Area</SelectItem>
              <SelectItem value="line">Line</SelectItem>
              <SelectItem value="bar">Bar</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <div className="grid gap-2">
          <Label htmlFor="algorithm">Trading Algorithm</Label>
          <Select
            value={config.algorithm}
            onValueChange={(value) => onConfigChange({ algorithm: value as Algorithm })}
          >
            <SelectTrigger id="algorithm">
              <SelectValue placeholder="Select algorithm" />
            </SelectTrigger>
            <SelectContent>
              {availableAlgorithms.map(algo => (
                  <SelectItem key={algo} value={algo}>{algo === 'None' ? 'None' : algo.replace(/([A-Z])/g, ' $1').trim()}</SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        <div className="grid gap-2">
          <Label>Indicators</Label>
          <div className="flex flex-col space-y-2">
            {availableIndicators.map((indicator) => (
              <div key={indicator} className="flex items-center space-x-2">
                <Checkbox
                  id={indicator}
                  checked={config.indicators.includes(indicator)}
                  onCheckedChange={(checked) => {
                    const newIndicators = checked
                      ? [...config.indicators, indicator]
                      : config.indicators.filter((i) => i !== indicator);
                    onConfigChange({ indicators: newIndicators });
                  }}
                />
                <Label htmlFor={indicator} className="font-normal">{indicator}</Label>
              </div>
            ))}
          </div>
        </div>
        <div className="text-sm">
          <div className="font-semibold mb-2">Algorithm Signals (Placeholder)</div>
          <div className="flex flex-wrap gap-2">
            <Badge variant="outline" className="border-green-500 text-green-500">BUY</Badge>
            <Badge variant="outline">HOLD</Badge>
            <Badge variant="outline" className="border-yellow-500 text-yellow-500">SMA Cross</Badge>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}

export default ChartConfigurationPanel
